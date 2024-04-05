<template>
  <q-page class="">
    <div>
      <q-table
        title="薪資結帳單列表"
        :rows="localList"
        :columns="processedColumns"
        row-key="name"
      >
        <template v-slot:body="props">
          <q-tr :props="props">
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
import { storeToRefs } from 'pinia';
import { QTableColumn } from 'quasar';
import { Business, useBusinessStore } from 'src/stores/business';
import { computed, ref, watch } from 'vue';
defineOptions({
  name: 'SalaryPage',
});

const businessStore = useBusinessStore();
const localList = ref<Business[]>([]);
const { list } = storeToRefs(businessStore);
businessStore.getList();
watch(list, (value) => {
  localList.value = value;
});

const updateRow = (data: Business, key: string, value: any) => {
  const payload = {
    ...data,
    [key]: value,
  };
  businessStore.update(payload);
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
