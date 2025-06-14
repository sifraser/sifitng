export enum ReportType {
    Cumulate,
    Progress,
    Summary
}

export enum Period {
    Year,
    Month,
    Week
}

export enum MetricType {
    Distance,
    Elevation,
    Time
}

function enumKeys<T extends object>(e: T) {
  const keys = Object.keys(e)
  const isStringEnum = isNaN(Number(keys[0]))
  return isStringEnum ? keys : keys.slice(keys.length / 2)
}

export let reportTypes = enumKeys(ReportType);
export let metricTypes = enumKeys(MetricType);
export let periods = enumKeys(Period);



