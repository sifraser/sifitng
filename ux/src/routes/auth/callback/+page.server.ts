import { STRAVA_CLIENT_ID, STRAVA_CLIENT_SECRET } from '$env/static/private';
import { redirect } from '@sveltejs/kit';
import { ClientCredentials, ResourceOwnerPassword, AuthorizationCode } from 'simple-oauth2';

export const load = async ({ url, cookies }) => {

  const code = url.searchParams.get('code')
  const scope = url.searchParams.get('scope')

  const config = {
    client: {
      id: STRAVA_CLIENT_ID,
      secret: STRAVA_CLIENT_SECRET
    },
    auth: {
      tokenHost: 'https://www.strava.com',
      tokenPath: '/oauth/token',
      authorizePath: '/oauth/authorize',
    }
  };

  const tokenParams = {
    client_id: STRAVA_CLIENT_ID,
    client_secret: STRAVA_CLIENT_SECRET,
    code: code,
    redirect_uri: 'http://localhost:5173/',
    scope: scope,
  };

  const client = new AuthorizationCode(config);
  var accessToken;
  try {
    accessToken = await client.getToken(tokenParams);
  } catch (error) {
    // TOOD -- properly handle!
    console.log('Access Token Error', error);
  }

  cookies.set('athleteId', accessToken.token.athlete.id, { path: '/' });

  // TODO -- store these in db keyed by athlete id
  cookies.set('accessToken', accessToken.token.access_token, { path: '/' });
  cookies.set('accessExpiry', accessToken.token.expires_at, { path: '/' });
  cookies.set('refreshToken', accessToken.token.refresh_token, { path: '/' });
  cookies.set('athleteName', accessToken.token.athlete.firstname + " " + accessToken.token.athlete.lastname, { path: '/' });

  redirect(302, '/')

}






