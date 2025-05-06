import TileWrapper from "./TileWrapper";

export default function Weight({ value }: { value: number }) {
    return (
        <TileWrapper title={'Weight'} span={1}  >
            <h3>{value}</h3>
        </TileWrapper>
    );
}