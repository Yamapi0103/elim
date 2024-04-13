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
  fare: number | null;
  extraCash: number | null;
  finalOrder: number | null;
  tip: number | null;
  taxes: number | null;
  orderer: string;
  reimbursement: number | null;
  memo: string;
  driverShare: number | null;
}

export enum exportType {
  BusinessList = 1,
  MonthSalaryReport = 2,
}

export type List = {
  list: Business[];
} & Pagination;

export interface ListParams {
  pageNum: number;
  pageSize: number;
  startDate: string;
  endDate: string;
  orderer: string;
  carNo: string;
  route: string;
}

export interface Pagination {
  pageNum: number;
  pageSize: number;
  totalCount: number;
  totalPages: number;
}

const initPagination = {
  pageNum: 1,
  pageSize: 10,
  totalCount: 0,
  totalPages: 0,
};

export const useBusinessStore = defineStore('business', () => {
  const list = ref<Business[]>([]);
  const startDate = ref(dayjs().startOf('month').format('YYYY-MM-DD'));
  const endDate = ref('');
  const orderer = ref(null);
  const carNo = ref(null);
  const route = ref('');
  const pagination = reactive<Pagination>(initPagination);
  const carNoOption = ref<string[]>([]);
  const ordererOption = ref<string[]>([]);

  const getListPageByFilter = async () => {
    const searchPayload = {
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize,
      startDate: startDate.value,
      endDate: endDate.value,
      orderer: orderer.value,
      carNo: carNo.value,
      route: route.value,
    };
    const { pageNum, pageSize, ...restParams } = searchPayload;
    const { data } = await api.post(
      `business/listPageByFilter?pageNum=${pageNum - 1}&pageSize=${pageSize}`,
      restParams
    );
    const { list: listFromResponse, ...rest } = data;
    list.value = listFromResponse;
    Object.assign(pagination, rest, { pageNum: rest.pageNum + 1 });
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

  const getCarNoOption = async () => {
    const { data } = await api.get('business/getCarNoOption');
    carNoOption.value = data;
  };

  const getCarOrdererOption = async () => {
    const { data } = await api.get('business/getOrdererOption');
    ordererOption.value = data;
  };

  const exportExcel = async (type: exportType) => {
    const exportPayload = {
      type,
      startDate: startDate.value,
      endDate: endDate.value,
      orderer: orderer.value,
      carNo: carNo.value,
      route: route.value,
    };
    await api.post('business/export', exportPayload);
  };

  const resetCondition = () => {
    startDate.value = dayjs().startOf('month').format('YYYY-MM-DD');
    endDate.value = '';
    orderer.value = null;
    carNo.value = null;
    route.value = '';
    Object.assign(pagination, {
      initPagination,
    });
  };

  return {
    list,
    update,
    add,
    remove,
    pagination,
    startDate,
    endDate,
    orderer,
    carNo,
    route,
    getListPageByFilter,
    resetCondition,
    getCarOrdererOption,
    getCarNoOption,
    ordererOption,
    carNoOption,
    exportExcel,
  };
});
