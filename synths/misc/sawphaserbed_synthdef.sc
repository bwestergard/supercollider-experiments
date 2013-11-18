Server.local.options.memSize = 50*8192;

SynthDef("simple", { arg note = 60,gate = 1; 

	var glide_root;
	var input_l,dsig_l, mixed_l;
	var input_r,dsig_r, mixed_r;
	var phase_control_l;
	var phase_control_r;
	var vol;

	glide_root = XLine.kr(note + Rand(-3,3),note,0.01) + LFNoise2.kr(1, 1/12);

	input_l = Saw.ar(glide_root.midicps,0.1) + Saw.ar((glide_root + 7).midicps,0.05) + Saw.ar((glide_root - 7).midicps,0.05);
	phase_control_l = SinOsc.ar(LFNoise2.kr(4, 0.2, 0.3),0,0.005,0.08);
	dsig_l = AllpassL.ar(input_l, 4, phase_control_l, 0);
	mixed_l = input_l + (dsig_l * 0.6);
	mixed_l = LPF.ar(mixed_l, 12000);

	input_r = Saw.ar(glide_root.midicps,0.1) + Saw.ar((glide_root + 7).midicps,0.05) + Saw.ar((glide_root - 7).midicps,0.05);
	phase_control_r = SinOsc.ar(LFNoise2.kr(4, 0.2, 0.3),0,0.005,0.08);
	dsig_r = AllpassL.ar(input_r, 4, phase_control_r, 0);
	mixed_r = input_r + (dsig_r * 0.6);
	mixed_r = LPF.ar(mixed_r, 12000);

	vol = EnvGen.kr(Env.adsr, gate, 1, doneAction: 2);

	Out.ar(0, mixed_l * Line.ar(0,0.7,0.1) * vol);
	Out.ar(1, mixed_r * Line.ar(0,0.7,0.1) * vol);

}).send(s);


//

(
var notes, on, off;
MIDIIn.connect;
notes = Array.newClear(128);  // array has one slot per possible MIDI note

on = Routine({

var event, newNode;

loop {

event = MIDIIn.waitNoteOn;

newNode = Synth("simple");
newNode.set(\note, event.b);
notes.put(event.b, newNode);

}

}).play;


off = Routine({

var event;

loop {

event = MIDIIn.waitNoteOff;

notes[event.b].set(\gate, 0);

}

}).play;

)
