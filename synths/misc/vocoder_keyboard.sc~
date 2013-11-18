s.boot;

(

x = SynthDef(\vocoder, {arg note, gate;

var a, b, chain1, chain2, out, carrier, bands;

bands = 2048;
a = LocalBuf.new(bands);
b = LocalBuf.new(bands);

carrier =
  Pulse.ar(note.midicps, width: 0.01) * 2
+ Pulse.ar(note.midicps * 3/2 + LFNoise2.ar(8,1/12), width: 0.1)
+ Pulse.ar(note.midicps * 2/3 + LFNoise2.ar(8,1/12), width: 0.1)
+ Pulse.ar(note.midicps / 2 + LFNoise2.ar(8,1/16), width: 0.1)
+ Pulse.ar(note.midicps * 2 + LFNoise2.ar(16,1/8), width: 0.1);

carrier = carrier * 0.2;

chain1 = FFT(a, carrier); // to be filtered

chain2 = FFT(b, AudioIn.ar(1));

// mouse x to play with floor. 

chain1 = PV_SpectralMap(chain1, chain2, 0, 0, 1, 1);
out = IFFT(chain1);

out = HPF.ar(out, 70, 0.1);

out = out * EnvGen.kr(Env.asr(3, 1, 2), gate, 1, doneAction: 2) * 43;

Out.ar(0, out.dup);

}).play(s, [\note, 50, \gate, 1]);

)

(

var notes, on, off;
MIDIIn.connect;
notes = Array.newClear(128);  // array has one slot per possible MIDI note

on = Routine({

var event, newNode;

loop {

event = MIDIIn.waitNoteOn;

newNode = Synth(\vocoder);
newNode.set(\note, event.b);
newNode.set(\gate, 1);
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

q = { on.stop; off.stop; };

)

q.value;
