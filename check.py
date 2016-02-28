# PYTHON 2.7

import subprocess, re

start = 1
end = 2000
errors_len = 0
while start <= end:
    output = subprocess.check_output(['java', 'ReciprocalCycles', str(start)])
    if '(' in output:
        out_cycle_len = output.split(' ')[-1]
        num_output = output[output.find("(")+1:output.find(")")]
        real_cycle_len = len(num_output)
        if real_cycle_len != int(out_cycle_len):
            print 'Length error on input ' + start
            errors_len += 1

    check_ans = 1/float(start)
    if check_ans != 1.0 and re.sub('[()]', '', output.split(' ')[2]).strip(',').strip('\n')[0:len(str(check_ans))-1] not in str(check_ans):
        print 'Numerical error on input ' + str(start)
        print re.sub('[()]', '', output.split(' ')[2]).strip(',').strip('\n')
        print str(check_ans)
        errors_len += 1
    start += 1

if not errors_len:
    print 'No errors'
