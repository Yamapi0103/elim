import { defineStore } from 'pinia';
import { api } from 'src/boot/axios';
import { ref } from 'vue';
export const getBusiness = (id: number) => api.get(`business/${id}`);

interface Business {
  id: number;
  carNo: string;
  date: string;
  route: string;
  fare: number;
  extraCash: number;
  finalOrder: string;
  tip: number | null;
  taxes: number | null;
  orderer: string;
  reimbursement: string;
  memo: string;
  driverShare: number | null;
}

export const useBusinessStore = defineStore('business', () => {
  const list = ref<Business[]>([]);

  const getList = async () => {
    const { data } = await api.get('business/list');
    list.value = data;
  };

  return {
    list,
    getList,
  };
});
