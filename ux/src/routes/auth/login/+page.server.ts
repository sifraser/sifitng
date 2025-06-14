import { STRAVA_CLIENT_ID, STRAVA_CLIENT_SECRET } from '$env/static/private';
import { ClientCredentials, ResourceOwnerPassword, AuthorizationCode } from 'simple-oauth2';
import { redirect } from '@sveltejs/kit';

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

const client = new AuthorizationCode(config);

const authorizationUri = client.authorizeURL({
  redirect_uri: 'http://localhost:5173/auth/callback',
  scope: 'activity:write,activity:read_all',
});

console.log('Authorize URL: ', authorizationUri);

redirect(302, authorizationUri);

