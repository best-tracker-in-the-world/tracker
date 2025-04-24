import { NextRequest, NextResponse } from "next/server";

export async function POST(request: NextRequest) {
	const body = await request.json();
	const { email, password } = body;

	if (typeof email === "string" && typeof password === "string") {
		return NextResponse.json({ message: "OK" }, { status: 200 });
	} else {
		return NextResponse.json({ error: "Invalid data" }, { status: 400 });
	}
}
