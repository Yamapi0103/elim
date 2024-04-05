<template>
  <q-page class="">
    <div>
      <div>薪資結帳單列表</div>
      <q-table :rows="list" :columns="columns" />
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { useBusinessStore } from 'src/stores/business';
import { storeToRefs } from 'pinia';
defineOptions({
  name: 'SalaryPage',
});

const businessStore = useBusinessStore();
const { list } = storeToRefs(businessStore);
businessStore.getList();

const columns = [
  {
    label: '日期',
    field: 'date',
    name: 'date',
  },
  {
    label: '車號',
    field: 'carNo',
    name: 'carNo',
  },
  {
    label: '行程',
    field: 'route',
    name: 'route',
  },
  {
    label: '車資',
    field: 'fare',
    name: 'fare',
  },
  {
    label: '外收現金',
    field: 'extraCash',
    name: 'extraCash',
  },
  // {

  // },
  {
    label: '小費',
    field: 'tip',
    name: 'tip',
  },
  {
    label: '稅金',
    field: 'taxes',
    name: 'taxes',
  },
  {
    label: '調車人',
    field: 'orderer',
    name: 'orderer',
  },
];
</script>
