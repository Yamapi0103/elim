import { api } from 'src/boot/axios';

export const getBusiness = (id: number) => api.get(`business/${id}`);
