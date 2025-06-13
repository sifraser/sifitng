enum ReportType {
    Cumulate,
    Progress,
    Summary
}

enum Period {
    Year,
    Month,
    Week
}

enum MetricType {
    Distance,
    Elevation,
    Time
}

// TODO -- fetch from server
enum ActivityType {
    AllRun,
    Run,
    AllRide,
    Kayak,
    Rowing
}


function enumKeys<T extends object>(e: T) {
  const keys = Object.keys(e)
  const isStringEnum = isNaN(Number(keys[0]))
  return isStringEnum ? keys : keys.slice(keys.length / 2)
}

export let reportTypes = enumKeys(ReportType);
export let activityTypes = enumKeys(ActivityType);
export let metricTypes = enumKeys(MetricType);
export let periods = enumKeys(Period);

