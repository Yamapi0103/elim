<template>
  <q-page class="row items-center justify-evenly">
    <q-uploader
      label="上傳 Excel 文件"
      accept=".xlsx, .xls"
      :factory="fileUploaded"
    />
  </q-page>
</template>

<script setup>
import { api } from 'src/boot/axios';

const fileUploaded = (files) => {
  const file = files[0];
  const formData = new FormData();
  formData.append('excel', file);
  api.post('excel/import', formData);
};
</script>
