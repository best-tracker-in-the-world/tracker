import TileWrapper from "@/components/widgets/dashboard/TileWrapper";

export default function Weight({ value, max }: { value: number, max: number }) {
    return (
        <TileWrapper title={'Goal'} span={1} >
            <h3>{value}/{max}</h3>
        </TileWrapper>

    );
}