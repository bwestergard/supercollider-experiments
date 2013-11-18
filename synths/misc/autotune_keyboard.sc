Server.local.options.memSize = 50*8192;

SynthDef("autotune", { arg note=50,gate=1;

var in, freq, hasFreq,out;

in=AudioIn.ar(1);

# freq, hasFreq = Tartini.kr(in);

freq = Clip.ar(freq, 40.midicps, 70.midicps);

//PrintVal.kr(freq.cpsmidi);
out = 	PitchShift.ar(
		in, 
		1/16, 		
		(note.midicps / freq),
		0,
		0.01
	) * 1;

out = out * EnvGen.kr(Env.adsr, gate, 1, doneAction: 2);

Out.ar(0, out);
Out.ar(1, out);

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

newNode = Synth("autotune");
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
