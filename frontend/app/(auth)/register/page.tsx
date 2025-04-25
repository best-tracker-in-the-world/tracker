"use client";

import AuthForm from "@/components/widgets/auth/Form";
import AuthLayout from "@/layouts/auth/layout";

export default function Login() {
	async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
		event.preventDefault();
		const formData = new FormData(event.target as HTMLFormElement);
		const data = Object.fromEntries(formData);

		try {
			const response = await fetch("/api/register", {
				method: "POST",
				body: JSON.stringify(data),
			});
			if (response.ok) {
				// TODO: REGISTER LOGIC
			}
		} catch (error) {
			console.error("error: ", error);
		}
	}
	return (
		<AuthLayout>
			<AuthForm handleSubmit={handleSubmit} type="register" />
		</AuthLayout>
	);
}
