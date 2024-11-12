// stores/auth.js
import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";

export const useAuthStore = defineStore("auth", () => {
  const user = ref({
    userSeq: null,
    userName: "",
    isAuthenticated: false,
  });

  const categories = ref([]);

  // 로그인 액션
  const login = async (loginData) => {
    try {
      const response = await axios.post(
        "http://localhost:8097/todo/api/user/login",
        loginData,
        { withCredentials: true }
      );
  
      const data = response.data;
  
      if (!data.accessToken) {
        throw new Error('로그인 응답에 토큰이 없습니다.');
      }
  
      // 유저 정보 저장
      user.value = {
        userSeq: data.userSeq,
        userName: data.userName,
        isAuthenticated: true,
      };
  
      // 토큰 설정
      sessionStorage.setItem("accessToken", data.accessToken);
      sessionStorage.setItem("userSeq", data.userSeq);
      sessionStorage.setItem("userName", data.userName);
      axios.defaults.headers.common["Authorization"] = `Bearer ${data.accessToken}`;
  
      // 카테고리 가져오기
      await fetchCategories();
  
      return { success: true };
  
    } catch (error) {
      let errorMessage = '로그인 처리 중 오류가 발생했습니다.';
  
      if (error.response) {
        if (error.response.status === 401) {
          errorMessage = '아이디 또는 비밀번호가 올바르지 않습니다.';
        } else {
          errorMessage = error.response.data.message || '서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.';
        }
      } else if (error.request) {
        errorMessage = '서버에 연결할 수 없습니다. 네트워크 연결을 확인해주세요.';
      }
  
      return {
        success: false,
        error: errorMessage
      };
    }
  };

  // 카테고리 가져오기
  const fetchCategories = async () => {
    const userSeq = sessionStorage.getItem('userSeq');
    const accessToken = sessionStorage.getItem('accessToken');
    try {
      const response = await axios.get(`http://localhost:8097/todo/api/category/list/${userSeq}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`
        }
      });
      if (response.data) {
        categories.value = response.data;
      } else {
        console.log('카테고리 조회 실패:', response.data);
      }
    } catch (error) {
      console.error('카테고리 조회 실패:', error.response?.data || error.message);
      if (error.response?.status === 401) {
        console.error('인증 토큰이 만료되었거나 유효하지 않습니다');
      }
    }
  }

  // 로그아웃
  const logout = () => {
    user.value = {
      userSeq: null,
      userName: "",
      isAuthenticated: false,
    };
    categories.value = [];
    sessionStorage.clear();
    delete axios.defaults.headers.common["Authorization"];
  };

  // 유저 정보 가져오기
  const fetchUserInfo = async () => {
    const userSeq = sessionStorage.getItem("userSeq");
    const token = sessionStorage.getItem("accessToken");

    if (!token || !userSeq) return;

    try {
      const response = await axios.get(
        `http://localhost:8097/todo/api/user/${userSeq}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (response.data) {
        user.value = {
          ...response.data,
          isAuthenticated: true,
        };
        // 유저 정보 가져온 후 카테고리도 가져오기
        await fetchCategories();
      }
    } catch (error) {
      console.error("유저 정보 조회 실패:", error);
      if (error.response?.status === 401) {
        logout(); // 인증 실패시 로그아웃
      }
    }
  };

  // 인증 상태 체크
  const checkAuth = async () => {
    const token = sessionStorage.getItem("accessToken");
    const userSeq = sessionStorage.getItem("userSeq");

    if (token && userSeq) {
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
      await fetchUserInfo();
    }
  };

  return {
    user, 
    categories, 
    login, 
    logout, 
    checkAuth,
    fetchCategories,
    fetchUserInfo
  };
});
