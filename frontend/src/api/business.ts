import { api } from 'src/boot/axios';

export const getList = () => api.get('business/list');

export const getBusiness = (id: number) => api.get(`business/${id}`);
