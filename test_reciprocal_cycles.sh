#!/bin/bash

file=ReciprocalCycles.java

if [ ! -f "$file" ]; then
    echo -e "Error: File '$file' not found.\nTest failed."
    exit 1
fi

num_right=0
total=0
line="________________________________________________________________________"
compiler=
interpreter=
language=
if [ "${file##*.}" = "py" ]; then
    if [ ! -z "$PYTHON_PATH" ]; then
        interpreter=$(which python.exe)
    else
        interpreter=$(which python3.2)
    fi
    command="$interpreter $file"
    echo -e "Testing $file\n"
elif [ "${file##*.}" = "java" ]; then
    language="java"
    command="java ${file%.java}"
    echo -n "Compiling $file..."
    javac $file
    echo -e "done\n"
fi

run_test_args() {
    (( ++total ))
    echo -n "Running test $total..."
    expected=$2
    received=$( $command $1 2>&1 | tr -d '\r' )
    if [ "$expected" = "$received" ]; then
        echo "success"
        (( ++num_right ))
    else
        echo -e "failure\n\nExpected$line\n$expected\nReceived$line\n$received\n"
    fi
}

run_test_args "" "Usage: java ReciprocalCycles <denominator>"
run_test_args "1 2 3" "Usage: java ReciprocalCycles <denominator>"
run_test_args "-13" "Error: Denominator must be an integer in [1, 2000]. Received '-13'."
run_test_args "euler" "Error: Denominator must be an integer in [1, 2000]. Received 'euler'."
run_test_args "1" "1/1 = 1"
run_test_args "2" "1/2 = 0.5"
run_test_args "3" "1/3 = 0.(3), cycle length 1"
run_test_args "4" "1/4 = 0.25"
run_test_args "5" "1/5 = 0.2"
run_test_args "6" "1/6 = 0.1(6), cycle length 1"
run_test_args "7" "1/7 = 0.(142857), cycle length 6"
run_test_args "8" "1/8 = 0.125"
run_test_args "9" "1/9 = 0.(1), cycle length 1"
run_test_args "10" "1/10 = 0.1"
run_test_args "11" "1/11 = 0.(09), cycle length 2"
run_test_args "12" "1/12 = 0.08(3), cycle length 1"
run_test_args "16" "1/16 = 0.0625"
run_test_args "23" "1/23 = 0.(0434782608695652173913), cycle length 22"
run_test_args "40" "1/40 = 0.025"
run_test_args "43" "1/43 = 0.(023255813953488372093), cycle length 21"
run_test_args "13" "1/13 = 0.(076923), cycle length 6"
run_test_args "14" "1/14 = 0.0(714285), cycle length 6"
run_test_args "27" "1/27 = 0.(037), cycle length 3"
run_test_args "29" "1/29 = 0.(0344827586206896551724137931), cycle length 28"
run_test_args "30" "1/30 = 0.0(3), cycle length 1"
run_test_args "36" "1/36 = 0.02(7), cycle length 1"
run_test_args "46" "1/46 = 0.0(2173913043478260869565), cycle length 22"
run_test_args "47" "1/47 = 0.(0212765957446808510638297872340425531914893617), cycle length 46"
run_test_args "48" "1/48 = 0.0208(3), cycle length 1"
run_test_args "49" "1/49 = 0.(020408163265306122448979591836734693877551), cycle length 42"
run_test_args "59" "1/59 = 0.(0169491525423728813559322033898305084745762711864406779661), cycle length 58"
run_test_args "61" "1/61 = 0.(016393442622950819672131147540983606557377049180327868852459), cycle length 60"
run_test_args "62" "1/62 = 0.0(161290322580645), cycle length 15"
run_test_args "64" "1/64 = 0.015625"
run_test_args "66" "1/66 = 0.0(15), cycle length 2"
run_test_args "67" "1/67 = 0.(014925373134328358208955223880597), cycle length 33"
run_test_args "68" "1/68 = 0.01(4705882352941176), cycle length 16"
run_test_args "97" "1/97 = 0.(010309278350515463917525773195876288659793814432989690721649484536082474226804123711340206185567), cycle length 96"
run_test_args "109" "1/109 = 0.(009174311926605504587155963302752293577981651376146788990825688073394495412844036697247706422018348623853211), cycle length 108"
run_test_args "113" "1/113 = 0.(0088495575221238938053097345132743362831858407079646017699115044247787610619469026548672566371681415929203539823), cycle length 112"

echo -e "\nTotal tests run: $total"
echo -e "Number correct : $num_right"
echo -n "Percent correct: "
echo "scale=2; 100 * $num_right / $total" | bc

if [ "$language" = "java" ]; then
   echo -e -n "\nRemoving class files..."
   rm -f *.class
   echo "done"
fi
