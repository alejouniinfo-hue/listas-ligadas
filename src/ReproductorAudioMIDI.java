import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class ReproductorAudioMIDI {

    private static final int VELOCIDAD = 100;
    private static final int CANAL_PIANO = 0;

    private static final int[] NOTAS_MIDI = {
            60, 61, 62, 63, 64, 65,
            66, 67, 68, 69, 70, 71
    };

    private static final int DURACION_REDONDA = 2000;
    private static final int DURACION_BLANCA = 1000;
    private static final int DURACION_NEGRA = 500;
    private static final int DURACION_CORCHEA = 300;

    private static Synthesizer synth;
    private static MidiChannel piano;

    public static void iniciarMelodia() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            piano = synth.getChannels()[CANAL_PIANO];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reproducirNota(NotaMusical notaMusical) {
        try {
            int notaMidi = NOTAS_MIDI[notaMusical.getNota().ordinal()]
                    + (notaMusical.getOctava() - 4) * 12;

            int duracion = obtenerDuracion(notaMusical.getFigura());

            piano.noteOn(notaMidi, VELOCIDAD);
            Thread.sleep(duracion);
            piano.noteOff(notaMidi);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static void reproducirLista(ListaLigada lista) {
        iniciarMelodia();

        Nodo actual = lista.getCabeza();

        while (actual != null) {
            reproducirNota(actual.dato);
            actual = actual.siguiente;
        }

        finalizarMelodia();
    }

    public static void finalizarMelodia() {
        synth.close();
    }

    private static int obtenerDuracion(Figura figura) {
        switch (figura) {
            case REDONDA:
                return DURACION_REDONDA;
            case BLANCA:
                return DURACION_BLANCA;
            case NEGRA:
                return DURACION_NEGRA;
            case CORCHEA:
                return DURACION_CORCHEA;
            default:
                return 400;
        }
    }
}