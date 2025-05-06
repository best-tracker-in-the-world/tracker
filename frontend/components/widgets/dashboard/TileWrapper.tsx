

export default function TileWrapper(


    { children, span, title }: { children: React.ReactNode, span: number, title: string }) {
    return (
        <div className={`min-w-10 min-h-10 ring-1 ring-gray-300 rounded-md`}>
            {children}
        </div>
    );
}