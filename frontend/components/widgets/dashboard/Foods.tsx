export default function Weight({ items }:
    { items: Array<{ name: string, }> }) {
    return (
        <h3>{
            items.map((item: string) => (
                <span key={item}>{item}s</span>
            ))
        }</h3>
    );
}