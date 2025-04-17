"use client";

import { Password } from "primereact/password";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import "../auth.css";

import { useState, useEffect } from "react";
import Link from "next/link";

export default function Login() {
	const [formData, setFormData] = useState({
		email: "",
		password: "",
	});

	const [isFormInvalid, setIsFormInvalid] = useState({
		email: false,
		password: false,
	});

	async function handleLogin() {
		console.log(formData);
		// REGISTER LOGIC HERE
		setIsSubmitted(true);
	}

	function isEmail(str: string) {
		return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(str);
	}

	// basic validation

	const [isSubmitDisabled, setIsSubmitDisabled] = useState(true);

	useEffect(() => {
		setIsSubmitDisabled(true);
		if (!isEmail(formData.email)) {
			return;
		}
		if (formData.password.length < 8) {
			return;
		}

		setIsFormInvalid({
			email: false,
			password: false,
		});
		setIsSubmitDisabled(false);
	}, [formData.email, formData.password]);

	// after register

	const [isSubmitted, setIsSubmitted] = useState(false);

	return (
		<div className="w-full h-screen flex justify-center items-center">
			<div className="form-wrap w-[90%] mx-auto ring-1 ring-slate-200 p-4 rounded-md">
				{isSubmitted && (
					<div className="flex flex-col gap-2 absolute left-1/2 top-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center">
						<p className="text-slate-600">
							Check your email to verify your account
						</p>
						<Link className="text-cyan-600" href="/login">
							Login
						</Link>
					</div>
				)}

				<form
					className={`flex flex-col gap-2 ${
						isSubmitted ? "opacity-0 pointer-events-none" : ""
					}`}
					onSubmit={(e) => e.preventDefault()}
				>
					<h2 className="text-cyan-600 font-bold opacity-40 w-full text-center text-3xl my-4">
						Register
					</h2>
					<InputText
						className="block min-w-full"
						invalid={isFormInvalid.email}
						onChange={(e) =>
							setFormData({
								...formData,
								email: e.target.value,
							})
						}
						placeholder="Email"
					/>
					<Password
						invalid={isFormInvalid.password}
						className="password-wrap block min-w-full *:w-full"
						onChange={(e) =>
							setFormData({
								...formData,
								password: e.target.value,
							})
						}
						placeholder="Password"
						feedback={false}
					/>
					<small className="flex gap-2 my-2 pl-2">
						<p className="text-slate-600">
							Already have an account?{" "}
						</p>
						<Link className="text-cyan-600" href="/login">
							Login
						</Link>
					</small>
					<Button
						className={isSubmitDisabled ? "opacity-25!" : ""}
						disabled={isSubmitDisabled}
						label="Register"
						onClick={handleLogin}
					/>
				</form>
			</div>
		</div>
	);
}
