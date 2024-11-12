// stores/auth.js
import { defineStore } from "pinia";
import { ref } from "vue";
import axios from "axios";
import { useRouter } from 'vue-router';

export const useCategoryStore = defineStore("category", () => {
    const router = useRouter();
    const categories = ref([]);
    const category = ref({});

    // 카테고리 목록 조회
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

    // 카테고리 상세 조회
    const fetchCategory = async (id) => {
        const accessToken = sessionStorage.getItem('accessToken');
        try {
            const response = await axios.get(`http://localhost:8097/todo/api/category/get/${id}`, {
                headers: { Authorization: `Bearer ${accessToken}` }
            });
            if (response.data) {
                category.value = response.data;
            }
        } catch (error) {
            console.error('카테고리 조회 실패:', error.response?.data || error.message);
        }
    }

    // 카테고리 등록
    const fetchAddCategory = async (categoryData) => {
        try {
            const accessToken = sessionStorage.getItem('accessToken');
            const response = await axios.post(
                'http://localhost:8097/todo/api/category/add',
                categoryData, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            }
            );
            if (response.data) {
                router.go(-1);
            }
        } catch (error) {
            console.error('카테고리 추가 실패:', error.response?.data || error.message);
            if (error.response?.status === 401) {
                alert('인증 토큰이 만료되었거나 유효하지 않습니다.');
            } else {
                alert('카테고리 추가에 실패했습니다.');
            }
        }
    };

    // 카테고리 수정
    const fetchCategoryUpdate = async () => {
        try {
            const accessToken = sessionStorage.getItem('accessToken');
            const response = await axios.put(
                `http://localhost:8097/todo/api/category/${category.value.id}`,
                category.value,
                {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                }
            );

            if (response.data) {
                router.go(-1);
            }
        } catch (error) {
            console.error('카테고리 수정 실패:', error.response?.data || error.message);
            if (error.response?.status === 401) {
                alert('인증 토큰이 만료되었거나 유효하지 않습니다.');
            } else {
                alert('카테고리 수정에 실패했습니다.');
            }
        }
    };

    // 카테고리 삭제
    const fetchCategoryDelete = async () => {
        try {
            const accessToken = sessionStorage.getItem('accessToken');
            const response = await axios.delete(
                `http://localhost:8097/todo/api/category/${category.value.id}`, {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            });
            if (response.status === 200) {
                router.push('/category-list');
            }
        } catch (error) {
            console.error('카테고리 삭제 실패:', error.response?.data || error.message);
            alert('카테고리 삭제에 실패했습니다.');
        }
    };

    return {
        categories,
        category,
        fetchCategories,
        fetchCategory,
        fetchAddCategory,
        fetchCategoryUpdate,
        fetchCategoryDelete,
    };
});
