// Execute the following in order
(
// allocate a Buffer
s = Server.local;
b = Buffer.alloc(s, 44100 * 0.1, 1); // a four second 1 channel Buffer
)

// record for four seconds
(
SynthDef("help-RecordBuf",{ arg out=0,bufnum=0;
	var formant,in,freq;
	in = AudioIn.ar(1);
	freq = Tartini.kr(in);
	// XLine will free the Synth when done
	RecordBuf.ar(in, bufnum, trigger: Impulse.ar(in));
}).play(s,[\out, 0, \bufnum, b.bufnum]);
)

// play it back
(
SynthDef("help-RecordBuf play",{ arg out=0,bufnum=0;
	var playbuf,in,freq;
	in = AudioIn.ar(1);
	freq = Tartini.kr(in);
	playbuf = PlayBuf.ar(1,bufnum, loop: 1, trigger: Impulse.ar(40.midicps));
//	FreeSelfWhenDone.kr(playbuf); // frees the synth when the PlayBuf is finished
	Out.ar(out, playbuf.dup);
}).play(s,[\out, 0, \bufnum, b.bufnum]);
)

// overdub
(
SynthDef("help-RecordBuf overdub",{ arg out=0,bufnum=0;
	var formant;
	// XLine will free the Synth when done
	formant = Formant.ar(XLine.kr(200, 1000, 4, doneAction: 2), 2000, 800, 0.125);
	RecordBuf.ar(formant, bufnum, 0, 0.5, 0.5); // mixes equally with existing data
}).play(s,[\out, 0, \bufnum, b.bufnum]);
)

// play back the overdubbed version
Synth.new("help-RecordBuf play", [\out, 0, \bufnum, b.bufnum], s);

// write the contents of the buffer to a file
b.write("recordings/RecordBuf-test.aiff", sampleFormat: 'int16');

b.close; b.free; // cleanup

