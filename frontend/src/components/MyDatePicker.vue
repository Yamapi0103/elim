<template>
  <q-input :label="props.label" filled v-model="modelValue">
    <template v-slot:append>
      <q-icon name="event" class="cursor-pointer">
        <q-popup-proxy>
          <q-date v-model="modelValue" mask="YYYY-MM-DD" />
        </q-popup-proxy>
      </q-icon>
    </template>
  </q-input>
</template>
<script setup lang="ts">
const props = defineProps(['label']);
const modelValue = defineModel<string>();
</script>
