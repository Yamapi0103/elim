import { defineStore } from 'pinia';
import { api } from 'src/boot/axios';
import { ref } from 'vue';
export const getBusiness = (id: number) => api.get(`business/${id}`);

export interface Business {
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

  const add = async (data: Omit<Business, 'id'>) => {
    await api.post('business/save', data);
  };

  const remove = async (id: number) => {
    await api.delete(`business/${id}`);
  };

  const update = async (data: Business) => {
    const { id } = data;
    await api.put(`business/${id}`, data);
  };
  return {
    list,
    getList,
    update,
    add,
    remove,
  };
});
