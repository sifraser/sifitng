<script>
	import GlobalNav from '$lib/GlobalNav.svelte';

  let { data } = $props();
</script>

<h1>Sifit</h1>

<GlobalNav data={ data }/>

<p>
Welcome { data.athleteName }!
</p>

