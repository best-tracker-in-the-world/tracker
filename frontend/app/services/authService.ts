interface AuthPayload {
	email: string;
	password: string;
}

export const login = (payload: AuthPayload) => {
	const apiUrl = useNuxtApp().$apiUrl;
	return $fetch(`${apiUrl}/auth/login`, {
		method: "POST",
		body: payload,
		throw: true,
	});
};

export const register = (payload: AuthPayload) => {
	const apiUrl = useNuxtApp().$apiUrl;
	console.log("Using API URL:", apiUrl);

	return $fetch(`${apiUrl}/auth/register`, {
		method: "POST",
		body: payload,
		throw: true,
	});
};
