# Mantém as classes usadas pelo Android
-keep class androidx.** { *; }
-keep class com.google.android.material.** { *; }
-keep class androidx.compose.** { *; }

# Remove logs em release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
