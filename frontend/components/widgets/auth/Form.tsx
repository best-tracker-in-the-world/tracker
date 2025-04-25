"use client";

import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import { useState, useEffect, FormEvent } from "react";
import Link from "next/link";
import useFormValidation from "@/hooks/useFormValidation";

interface AuthFormProps {
	type: "login" | "register";
	handleSubmit: (event: FormEvent<HTMLFormElement>) => void;
}

export default function AuthForm({ type, handleSubmit }: AuthFormProps) {
	const [isLoaded, setIsLoaded] = useState(false);
	const [formData, setFormData] = useState({ email: "", password: "" });
	const isValid = useFormValidation(formData);

	const url = type === "login" ? "/register" : "/login";

	const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const { name, value } = event.target;
		setFormData({ ...formData, [name]: value });
	};

	useEffect(() => {
		setIsLoaded(true);
	}, []);

	function handleLinkClick() {
		setIsLoaded(false);
	}

	return (
		<form
			className={`${
				isLoaded ? "auth-form slide-up" : "slide-down"
			} flex flex-col gap-4 px-4 opacity-0 py-6 ring-1 ring-gray-200 rounded-md`}
			onSubmit={(event) =>
				handleSubmit(event as FormEvent<HTMLFormElement>)
			}
		>
			<h1 className="w-full text-center text-2xl capitalize">
				{type}
			</h1>
			<InputText
				value={formData.email}
				name="email"
				onChange={handleInputChange}
				placeholder="Email"
			/>
			<Password
				onChange={handleInputChange}
				name="password"
				value={formData.password}
				placeholder="Password"
				feedback={false}
			/>
			<small className="pl-2">
				<span>
					{type === "login"
						? "Don't have an account?"
						: "Already have an account?"}
				</span>
				<Link
					onClick={handleLinkClick}
					href={url}
					className="text-blue-500"
				>
					{" "}
					{type === "login" ? "Register" : "Login"}
				</Link>
			</small>
			<Button
				label={type === "login" ? "Login" : "Register"}
				disabled={!isValid}
			/>
		</form>
	);
}
