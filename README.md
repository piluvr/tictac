# tictac
MainActivity-
android.media.AudioManager audioManager = (android.media.AudioManager) getSystemService(Context.AUDIO_SERVICE); audioManager.setStreamVolume(android.media.AudioManager.STREAM_MUSIC, 20, 0);
AndroidManifest-
<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
