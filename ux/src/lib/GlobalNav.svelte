<script>
	import { reportTypes, metricTypes, periods } from './navigation.ts';
  let { data } = $props();

  import stores from './stores.js';
  let activityType = $state();
  let metricType = $state();
  let period = $state();
  stores.activityType.subscribe(value => {
      activityType = value;
  });
  stores.metricType.subscribe(value => {
      metricType = value;
  });
  stores.period.subscribe(value => {
      period = value;
  });
  stores.activityType.update(value => {
    return value == null ? data.activityTypes[0] : value;
  });
  stores.metricType.update(value => {
    return value == null ? metricTypes[0] : value;
  });
  stores.period.update(value => {
    return value == null ? periods[0] : value;
  });

	let reportType = $state(reportTypes[0]);
	let otherReportTypes = $derived(reportTypes.filter(rt => rt !== reportType));

	let otherActivityTypes = $derived(data.activityTypes.filter(at => at !== activityType));
	let otherMetricTypes = $derived(metricTypes.filter(mt => mt !== metricType));
	let otherPeriods = $derived(periods.filter(p => p !== period));

</script>

<style>

#nav li {
		  margin: 0;
	  padding: 0;
	  float: left;
	}


#globalNav ul {
  position: -webkit-sticky; /* Safari */
  position: sticky;
  top: 0;
  width: 100%;
    height: 44px;
      max-width: 1000px;
	  list-style-type: none;
	  margin: 0;
	  padding: 0;
	  background-color: #f9f9f9;
	}



#globalNav li {
		  margin: 0;
	  padding: 0;
	  float: left;
	}

.dropbtn {
  border: none;
  cursor: pointer;
  	  display: block;
	  color: #333;
	  text-align: center;
	  padding: 10px 10px;
	  	  height: 44px;
	  text-decoration: none;
	      font-weight: bold;
		  font-size: 12pt;
		  font-family: georgia, serif;
		    background-color: #f9f9f9;
}

/* The container <div> - needed to position the dropdown content */
.dropdown {
  position: relative;
  display: inline-block;
}

/* Dropdown Content (Hidden by Default) */
.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

/* Links inside the dropdown */
.dropdown-content button {
  color: black;
  padding: 10px 10px;
  text-decoration: none;
  display: block;
}

/* Change color of dropdown links on hover */
.dropdown-content button:hover {background-color: #f1f1f1}

/* Show the dropdown menu on hover */
.dropdown:hover .dropdown-content {
  display: block;
}

/* Change the background color of the dropdown button when the dropdown content is shown */
.dropdown:hover .dropbtn {
  background-color: #f1f1f1;
}

</style>


<div id='globalNav'>
    <ul>
        <li>
            <div class='dropdown'>
                <button class='dropbtn'>{reportType}</button>
                <div class='dropdown-content'>
                    {#each otherReportTypes as rt}
                        <button
                            class='dropbtn'
                            onclick={() => reportType = rt}
                        >{rt}</button>
                    {/each}
                </div>
            </div>
        </li>
        <li>
            <div class='dropdown'>
                <button class='dropbtn'>{activityType}</button>
                <div class='dropdown-content'>
                    {#each otherActivityTypes as at}
                        <button
                            class='dropbtn'
                            onclick={() => stores.activityType.set(at)}
                        >{at}</button>
                    {/each}
                </div>
            </div>
        </li>
        <li>
            <div class='dropdown'>
                <button class='dropbtn'>{metricType}</button>
                <div class='dropdown-content'>
                    {#each otherMetricTypes as mt}
                        <button
                            class='dropbtn'
                            onclick={() => stores.metricType.set(mt)}
                        >{mt}</button>
                    {/each}
                </div>
            </div>
        </li>
        <li>
            <div class='dropdown'>
                <button class='dropbtn'>{period}</button>
                <div class='dropdown-content'>
                    {#each otherPeriods as p}
                        <button
                            class='dropbtn'
                            onclick={() => stores.period.set(p)}
                        >{p}</button>
                    {/each}
                </div>
            </div>
        </li>
    </ul>
</div>
