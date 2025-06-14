<script>
	import GlobalNav from '$lib/GlobalNav.svelte';

  let { data } = $props();
</script>

<h1>Sifit</h1>

<GlobalNav/>

<p>
Welcome { data.athleteName }!
</p>

