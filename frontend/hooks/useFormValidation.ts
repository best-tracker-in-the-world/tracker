import { useState, useEffect } from "react";

const useFormValidation = (formData: { email: string; password: string }) => {
	const [isValid, setIsValid] = useState(false);

	useEffect(() => {
		const { email, password } = formData;

		// TODO: REPLACE WITH PROPER VALIDATION!

		// const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		// const isEmailValid = emailRegex.test(email);
		const isEmailValid = email.length >= 1;
		const isPasswordValid = password.length >= 1;
		setIsValid(isEmailValid && isPasswordValid);
	}, [formData]);

	return isValid;
};

export default useFormValidation;
