import dayjs from 'dayjs';
import { defineStore } from 'pinia';
import { api } from 'src/boot/axios';
import { reactive, ref } from 'vue';
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

export interface List {
  list: Business[];
  pageNum: number;
  pageSize: number;
  totalCount: number;
  totalPages: number;
}

export interface ListParams {
  pageNum: number;
  pageSize: number;
  startDate: string;
  endDate: string;
  orderer: string;
  carNo: string;
  route: string;
}

const initPagination = {
  pageNum: 1,
  pageSize: 5,
  totalCount: 0,
  totalPages: 0,
};

export const useBusinessStore = defineStore('business', () => {
  const list = ref<Business[]>([]);
  const startDate = ref(dayjs().startOf('month').format('YYYY-MM-DD'));
  const endDate = ref(dayjs().format('YYYY-MM-DD'));
  const orderer = ref('');
  const carNo = ref('');
  const route = ref('');
  const pagination = reactive(initPagination);

  const filterCondition = {
    startDate,
    endDate,
    pagination,
  };
  const getList = async () => {
    const { data } = await api.get('business/list');
    list.value = data;
  };
  const getListPageByFilter = async (params: ListParams) => {
    const { pageNum, pageSize, ...restParams } = params;
    const { data } = await api.post(
      `business/listPageByFilter?pageNum=${pageNum}&pageSize=${pageSize}`,
      restParams
    );
    const { list, ...rest } = data;
    list.value = list;
    Object.assign(pagination, rest);
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

  const resetCondition = () => {
    startDate.value = dayjs().startOf('month').format('YYYY-MM-DD');
    endDate.value = dayjs().format('YYYY-MM-DD');
    orderer.value = '';
    carNo.value = '';
    route.value = '';
    Object.assign(pagination, {
      initPagination,
    });
  };

  return {
    list,
    getList,
    update,
    add,
    remove,
    pagination,
    startDate,
    endDate,
    filterCondition,
    orderer,
    carNo,
    route,
    getListPageByFilter,
    resetCondition
  };
});
