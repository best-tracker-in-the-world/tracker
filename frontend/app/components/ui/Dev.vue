<!-- USE TO DISPLAY DEBUG INFORMATION -->
<!-- ONLY VISIBLE WHEN RUNNING WITH "npm run dev" -->
<template>
	<DevOnly>
		<div class="dev-only">
			<slot />
		</div>
	</DevOnly>
</template>

<style scoped>

.dev-only {
	outline: 1px dashed red;
	outline-offset: 2px;
	display: inline;
	padding: 4px 8px;
	font-size: 14px;
	border-radius: 4px;
	position: relative;
	width: fit-content;
}
.dev-only::after {
	content: 'Dev only';
	position: absolute;
	font-size: 8px;
	white-space: nowrap;
	top: -16px;
	left: 0;
	color: red;
	opacity: .75;
}

</style>