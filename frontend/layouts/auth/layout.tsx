import IconThemeSwitch from "@/components/ui/themeToggle/iconSwitch";
export default function AuthWrap({ children }: { children: React.ReactNode }) {
	return (
		<div className="w-full h-screen grid items-center justify-center">
			<div className="absolute bottom-4 right-1/2 translate-x-1/2">
				<IconThemeSwitch />
			</div>
			{children}
		</div>
	);
}
