"use client";

import AuthForm from "@/components/widgets/auth/Form";
import AuthLayout from "@/layouts/auth/layout";
import { useUserStore } from "@/stores/userStore";
import { useRouter } from "next/navigation";

export default function Login() {
	const router = useRouter();

	async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
		event.preventDefault();
		const formData = new FormData(event.target as HTMLFormElement);
		const data = Object.fromEntries(formData);

		try {
			const response = await fetch("/api/login", {
				method: "POST",
				body: JSON.stringify(data),
			});
			if (response.ok) {
				const result = await response.json();

				useUserStore.setState({ name: result.data.name });
				useUserStore.setState({ id: result.data.id });
				useUserStore.setState({ email: result.data.email });

				router.push("/dashboard");
			}
		} catch (error) {
			console.error("error: ", error);
		}
	}
	return (
		<AuthLayout>
			<AuthForm handleSubmit={handleSubmit} type="login" />
		</AuthLayout>
	);
}
