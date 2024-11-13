// stores/auth.js
import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

export const useAuthStore = defineStore("auth", () => {
  const router = useRouter();
  const user = ref({
    userSeq: null,
    userId: null,
    userName: "",
    isAuthenticated: false,
  });

  const getToken = () => sessionStorage.getItem('accessToken');

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
        userId: data.userId,
        userName: data.userName,
        isAuthenticated: true,
      };

      // 토큰 설정
      sessionStorage.setItem("accessToken", data.accessToken);
      sessionStorage.setItem("userSeq", data.userSeq);
      axios.defaults.headers.common["Authorization"] = `Bearer ${data.accessToken}`;

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

  // 로그아웃
  const logout = () => {
    user.value = {
      userSeq: null,
      userId: null,
      userName: "",
      isAuthenticated: false,
    };
    sessionStorage.clear();
    delete axios.defaults.headers.common["Authorization"];
    router.push('/');
  };

  // 유저 정보 가져오기
  const fetchUserInfo = async () => {
    const accessToken = getToken();
    const userSeq = sessionStorage.getItem('userSeq');
    if (!accessToken || !userSeq) return;

    try {
      const response = await axios.get(
        `http://localhost:8097/todo/api/user/${userSeq}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );

      if (response.data) {
        user.value = {
          ...response.data,
          isAuthenticated: true,
        };
      }
    } catch (error) {
      console.error("유저 정보 조회 실패:", error);
      if (error.response?.status === 401) {
        logout();
      }
    }
  };

  // 인증 상태 체크
  const checkAuth = async () => {
    const accessToken = getToken();
    const userSeq = sessionStorage.getItem('userSeq');

    if (accessToken && userSeq) {
      axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
      await fetchUserInfo();
    }
  };

  return {
    user,
    login,
    logout,
    checkAuth,
    fetchUserInfo,
    getToken,
  };
});
