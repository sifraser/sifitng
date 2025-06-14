	import { redirect } from '@sveltejs/kit';

	export function load({ cookies }) {

    if (cookies.get('athleteId') == null) {
      redirect(302, '/auth/login');
    }

    // TODO -- fetch from db using athleteId
    return {
      athleteName: cookies.get('athleteName')
    }
  }


