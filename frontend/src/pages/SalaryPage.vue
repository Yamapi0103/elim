<template>
  <q-page class="">
    <div>
      <div>Salary page</div>
      <q-btn
        class="q-mt-xl"
        color="white"
        text-color="blue"
        unelevated
        to="/"
        label="Go Home"
        no-caps
      />
    </div>
  </q-page>
</template>

<script setup lang="ts">
import { getBusiness } from '../api/request';
defineOptions({
  name: 'SalaryPage',
});

getBusiness(6);
</script>
