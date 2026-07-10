interface Props {

    title: string;

    value: number;

}

export default function DashboardCard({

    title,

    value

}: Props) {

    return (

        <div className="border rounded-lg p-5 shadow">

            <h3 className="text-gray-500">

                {title}

            </h3>

            <h1 className="text-3xl font-bold">

                {value}

            </h1>

        </div>

    );

}