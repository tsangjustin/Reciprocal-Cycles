# PYTHON 2.7
# checks to make sure length is correct

import subprocess

start = 1
end = 2000
errors_len = 0
while start <= end:
    output = subprocess.check_output(['java', 'ReciprocalCycles', str(start)])
    if '(' in output:
        out_cycle_len = output.split(' ')[-1]
        real_cycle_len = len(output[output.find("(")+1:output.find(")")])
        if real_cycle_len != int(out_cycle_len):
            print 'Error on input ' + start
            errors_len += 1
    start += 1

if not errors_len:
    print 'No errors'
