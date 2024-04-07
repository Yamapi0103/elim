<template>
  <q-page>
    <div class="q-mb-md q-mr-sm">
      <div class="text-h6 q-pa-sm">篩選條件</div>
      <q-form @submit="onSearch" @reset="resetCondition" class="q-gutter-md">
        <MyDatePicker label="起始日期" v-model="startDate" />
        <MyDatePicker label="結束日期" v-model="endDate" />
        <q-select label="車號" v-model="carNo" :options="carNoOption" />
        <q-select label="調車人" v-model="orderer" :options="ordererOption" />
        <q-input label="行程" v-model="route" />
        <div>
          <q-btn label="搜尋" type="submit" color="primary" />
          <q-btn
            label="重置"
            type="reset"
            color="primary"
            flat
            class="q-ml-sm"
          />
        </div>
      </q-form>
    </div>
    <div>
      <q-table
        title="薪資結帳單列表"
        :rows="localList"
        :columns="processedColumns"
        row-key="id"
        selection="single"
        v-model:selected="selected"
        v-model:pagination="qPaginationMap"
        @request="onPageChange"
      >
        <template v-slot:top>
          <q-btn
            class="q-mr-sm"
            color="primary"
            label="新增列"
            @click="addRow"
          />
          <q-btn
            color="negative"
            :disable="!hasSelected"
            label="刪除列"
            @click="removeRow"
          />
          <q-space />
        </template>
        <template v-slot:body="props">
          <q-tr :props="props">
            <q-td>
              <q-checkbox v-model="props.selected" />
            </q-td>
            <q-td key="date" :props="props">
              {{ props.row.date }}
              <q-popup-edit
                v-model="props.row.date"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'date', value)"
              >
                <q-date v-model="scope.value" mask="YYYY-MM-DD" />
              </q-popup-edit>
            </q-td>
            <q-td key="carNo" :props="props">
              {{ props.row.carNo }}
              <q-popup-edit
                v-model="props.row.carNo"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'carNo', value)"
              >
                <q-input
                  v-model="scope.value"
                  dense
                  autofocus
                  counter
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
            <q-td key="route" :props="props">
              {{ props.row.route }}
              <q-popup-edit
                v-model="props.row.route"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'route', value)"
              >
                <q-input
                  v-model="scope.value"
                  dense
                  autofocus
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
            <q-td key="fare" :props="props">
              {{ props.row.fare }}
              <q-popup-edit
                v-model="props.row.fare"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'fare', value)"
              >
                <q-input
                  v-model="scope.value"
                  type="number"
                  dense
                  autofocus
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
            <q-td key="extraCash" :props="props">
              {{ props.row.extraCash }}
              <q-popup-edit
                v-model="props.row.extraCash"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'extraCash', value)"
              >
                <q-input
                  v-model="scope.value"
                  type="number"
                  dense
                  autofocus
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
            <q-td key="finalOrder" :props="props">
              {{ props.row.finalOrder }}
              <q-popup-edit
                v-model="props.row.finalOrder"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'finalOrder', value)"
              >
                <q-input
                  v-model="scope.value"
                  type="number"
                  dense
                  autofocus
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
            <q-td key="tip" :props="props">
              {{ props.row.tip }}
              <q-popup-edit
                v-model="props.row.tip"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'tip', value)"
              >
                <q-input
                  v-model="scope.value"
                  type="number"
                  dense
                  autofocus
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
            <q-td key="taxes" :props="props">
              {{ props.row.taxes }}
              <q-popup-edit
                v-model="props.row.taxes"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'taxes', value)"
              >
                <q-input
                  v-model="scope.value"
                  type="number"
                  dense
                  autofocus
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
            <q-td key="orderer" :props="props">
              {{ props.row.orderer }}
              <q-popup-edit
                v-model="props.row.orderer"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'orderer', value)"
              >
                <q-input
                  v-model="scope.value"
                  dense
                  autofocus
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
            <q-td key="reimbursement" :props="props">
              {{ props.row.reimbursement }}
              <q-popup-edit
                v-model="props.row.reimbursement"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'reimbursement', value)"
              >
                <q-input
                  v-model="scope.value"
                  type="number"
                  dense
                  autofocus
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
            <q-td key="memo" :props="props">
              {{ props.row.memo }}
              <q-popup-edit
                v-model="props.row.memo"
                buttons
                v-slot="scope"
                @save="(value) => updateRow(props.row, 'memo', value)"
              >
                <q-input
                  v-model="scope.value"
                  dense
                  autofocus
                  @keyup.enter="scope.set"
                />
              </q-popup-edit>
            </q-td>
          </q-tr>
        </template>
      </q-table>
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { QTableColumn } from 'quasar';
import { Business, useBusinessStore } from 'src/stores/business';
import { computed, ref } from 'vue';
import dayjs from 'dayjs';
import { storeToRefs } from 'pinia';
import MyDatePicker from 'components/MyDatePicker.vue';
defineOptions({
  name: 'SalaryPage',
});

const businessStore = useBusinessStore();
const localList = ref<Business[]>([]);
const selected = ref<Business[]>([]);

const {
  startDate,
  endDate,
  orderer,
  carNo,
  route,
  pagination,
  carNoOption,
  ordererOption,
} = storeToRefs(businessStore);

console.log('carNo===', carNo);
const onSearch = () => {
  businessStore.getListPageByFilter();
};

onSearch();
businessStore.getCarNoOption();
businessStore.getCarOrdererOption();

const qPaginationMap = computed({
  get() {
    const {
      pageNum: page,
      totalCount: rowsNumber,
      pageSize: rowsPerPage,
    } = pagination.value;
    return {
      page,
      rowsPerPage,
      rowsNumber,
    };
  },
  set() {
    return;
  },
});

const paginationFromQPagination = (props: any) => {
  const { page: pageNum, rowsPerPage: pageSize } = props.pagination;
  return { pageNum, pageSize };
};

const onPageChange = (props: any) => {
  const { pageNum, pageSize } = paginationFromQPagination(props);
  businessStore.$patch({
    pagination: {
      pageNum,
      pageSize,
    },
  });
  onSearch();
};

businessStore.$subscribe((_mutation, state) => {
  localList.value = state.list;
});

const hasSelected = computed(() => selected.value.length > 0);
const updateRow = (data: Business, key: string, value: any) => {
  const updatePayload = {
    ...data,
    [key]: value,
  };
  businessStore.update(updatePayload);
};

const addRow = async () => {
  const row = {
    carNo: '',
    date: dayjs().format('YYYY-MM-DD'),
    route: '',
    fare: 0,
    extraCash: 0,
    finalOrder: '',
    tip: 0,
    taxes: 0,
    orderer: '',
    reimbursement: '',
    memo: '',
    driverShare: null,
  };
  await businessStore.add(row);
  onSearch();
};

const removeRow = async () => {
  if (!hasSelected.value) return;
  await businessStore.remove(selected.value[0].id);
  onSearch();
};

const resetCondition = () => {
  businessStore.resetCondition();
  onSearch();
};

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
  {
    label: '簽單',
    field: 'finalOrder',
    name: 'finalOrder',
  },
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
  {
    label: '公帳支出',
    field: 'reimbursement',
    name: 'reimbursement',
  },
  {
    label: '備註',
    field: 'memo',
    name: 'memo',
  },
];
const processedColumns = computed<QTableColumn[]>(() => {
  return columns.map((column) => ({ ...column, align: 'left' }));
});
</script>
