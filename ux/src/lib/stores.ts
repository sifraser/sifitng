import { writable } from 'svelte/store';
import { Period, MetricType } from './navigation.ts';

export const activityType = writable<String | null>(null)
export const period = writable<Period | null>(null)
export const metricType = writable<MetricType | null>(null)

export default {
  activityType,
  period,
  metricType,
}