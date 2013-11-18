{

var in, freq, hasFreq,out,trig,notes;
trig = Impulse.ar(MouseX.kr(1,16));
notes = Dseq([0,4,7,12], inf);

in=AudioIn.ar(1);

# freq, hasFreq = Tartini.kr(in);

freq = Clip.ar(freq, 40.midicps, 70.midicps);

PrintVal.kr(freq.cpsmidi);
out = 	PitchShift.ar(
		in, 
		0.1, 		
		((Demand.ar(trig, 0, notes) + 50).midicps / freq),
		0,
		0.01
	);

out.dup;

}.play;

{

var in, freq, hasFreq,out,trig,notes;
trig = Impulse.ar(MouseX.kr(1,8)*2);
notes = Dseq([9,7,9,7], inf);

in=AudioIn.ar(1);

# freq, hasFreq = Tartini.kr(in);

freq = Clip.ar(freq, 40.midicps, 70.midicps);

PrintVal.kr(freq.cpsmidi);
out = 	PitchShift.ar(
		in, 
		0.1, 		
		((Demand.ar(trig, 0, notes) + 54).midicps / freq),
		0,
		0.01
	);

[out,out]

}.play;

{

var in, freq, hasFreq,out,trig,notes;
trig = Impulse.ar(MouseX.kr(1,8));
notes = Dseq([12,-12,-12,-12,-12], inf);

in=AudioIn.ar(1);

# freq, hasFreq = Tartini.kr(in);

freq = Clip.ar(freq, 40.midicps, 70.midicps);

PrintVal.kr(freq.cpsmidi);
out = 	PitchShift.ar(
		in, 
		0.1, 		
		((Demand.ar(trig, 0, notes) + 54).midicps / freq),
		0,
		0.01
	);

[out,out]

}.play;

