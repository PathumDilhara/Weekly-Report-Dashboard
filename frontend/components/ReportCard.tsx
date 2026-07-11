import Link from "next/link";


export default function ReportCard({
    report
}: any) {


    return (

        <Link

            href={`/reports/${report.id}`}

            className="
block
border
rounded-lg
p-5
hover:shadow
transition
"


        >


            <h2 className="font-bold">

                {report.projectName}

            </h2>


            <p>
                Week:
                {report.weekStart}
                -
                {report.weekEnd}
            </p>


            <span>

                {report.status}

            </span>



        </Link>

    )

}