

// anazlyze a soundfile and store its data to a buffer


s = Server.local;

s.boot;


(

var sf;

// path to a sound file here

p = "/home/bjorn/audio_work/samples/j5aca.wav";

// the frame size for the analysis - experiment with other sizes (powers of 2)

f = 1024; 

// the hop size

h = 1;

// get some info about the file

sf = SoundFile.new( p );

sf.openRead;

sf.close;

// allocate memory to store FFT data to... SimpleNumber.calcPVRecSize(frameSize, hop) will return 

// the appropriate number of samples needed for the buffer

y = Buffer.alloc(s, sf.duration.calcPVRecSize(f, h));

// allocate the soundfile you want to analyze

z = Buffer.read(s, p);

)


// this does the analysis and saves it to buffer 1... frees itself when done

(

SynthDef("pvrec", { arg recBuf=1, soundBufnum=2;

var in, chain, bufnum;

bufnum = LocalBuf.new(1024);

Line.kr(1, 1, BufDur.kr(soundBufnum), doneAction: 2);

in = PlayBuf.ar(1, soundBufnum, BufRateScale.kr(soundBufnum), loop: 0);

// note the window type and overlaps... this is important for resynth parameters

chain = FFT(bufnum, in, 0.25, 1); 

chain = PV_RecordBuf(chain, recBuf, 0, 1, 0, 0.25, 1);

// no ouput ... simply save the analysis to recBuf

}).send(s);

)

a = Synth("pvrec", [\recBuf, y, \soundBufnum, z]);


// you can save your 'analysis' file to disk! I suggest using float32 for the format

// These can be read back in using Buffer.read


y.write(p++".scpv", "wav", "float32");


// play your analysis back ... see the playback UGens listed above for more examples.


SynthDef("pvplay", { arg out=0, recBuf=1;

var in, chain, bufnum, foo, freq, carrier, a, b, c_chain;

a = LocalBuf.new(1024);
b = LocalBuf.new(1024);

freq = 60.midicps;

carrier = Impulse.ar(freq*2, width: 0.1);
carrier = carrier * 0.3;

c_chain = FFT(a, carrier);

chain = PV_BufRd(b, recBuf, (MouseX.kr(1/32,1)));
//chain = PV_SpectralMap(chain, c_chain, 0.0, 0, 1, 1);
foo = IFFT(chain, 1);
//foo = MoogLadder.ar(foo, MouseButton.kr(100,20000,0.1), 0.2);


Out.ar(out, foo.dup);
}).send(s);


b = Synth("pvplay", [\out, 0, \bufnum, x, \recBuf, y]);


// stop the synth

b.free;
