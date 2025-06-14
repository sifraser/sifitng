	import { redirect } from '@sveltejs/kit';

	export const load: PageLoad = async({ cookies, fetch }) => {
    if (cookies.get('athleteId') == null) {
      redirect(302, '/auth/login');
    }

    // TODO -- fetch for specific athleteId
    const res = await fetch(`http://localhost:8080/activityTypes`);
    const activityTypes = await res.json();

    // TODO -- fetch from db/api using athleteId
    return {
      athleteName: cookies.get('athleteName'),
      activityTypes: activityTypes
    }
  }


