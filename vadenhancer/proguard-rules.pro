# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

#all firebase serialized/deserialized model classes
-keep class com.firebase.** { *; }
-keep class com.google.** { *; }
-keep class com.google.firebase.firestore.**{*;}

# keep all model classes
-keep class com.strangers.chat_developers.strangerschatapp.ModelClasses.** { *; }
-keep class com.strangers.chat_developers.strangerschatapp.Activities.EditUserProfile**{ *; }
-keep class org.apache**{ *; }
-dontwarn org.apache.**

-keep, includedescriptorclasses  class in.uncod.android.bypass.Document { *; }
-keep, includedescriptorclasses class in.uncod.android.bypass.Element { *; }

-keepattributes SourceFile, LineNumberTable

# ads down
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
# ads up

# agora down
-keep class io.agora.**{*;}
# agora up


# Retrofit down
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
# Retrofit up



# iron source down
-keepclassmembers class com.ironsource.sdk.controller.IronSourceWebView$JSInterface {
    public *;
}
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
-keep public class com.google.android.gms.ads.** {
   public *;
}
-keep class com.ironsource.adapters.** { *;
}
-dontwarn com.ironsource.mediationsdk.**
-dontwarn com.ironsource.adapters.**
-keepattributes JavascriptInterface
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
# iron source up


#adcolony down
# For communication with AdColony's WebView
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
# For removing warnings due to lack of Multi-Window support
-dontwarn android.app.Activity
#adcolony up


#mytarget down
-keep class com.my.target.** {*;}
#mytarget up

#pangle down
-keep class com.bytedance.sdk.** { *; }
#pangle up


#startapp up
-keep class com.startapp.** {
      *;
}

-keep class com.truenet.** {
      *;
}

-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile,
LineNumberTable, *Annotation*, EnclosingMethod
-dontwarn android.webkit.JavascriptInterface
-dontwarn com.startapp.**

-dontwarn org.jetbrains.annotations.**
#startapp up


-keep class com.my.target.** {*;}

-keepattributes SourceFile,LineNumberTable
-keep class com.inmobi.** { *; }
-keep public class com.google.android.gms.**
-dontwarn com.google.android.gms.**
-dontwarn com.squareup.picasso.**
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient{
     public *;
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info{
     public *;
}
# skip the Picasso library classes
-keep class com.squareup.picasso.** {*;}
-dontwarn com.squareup.okhttp.**
# skip Moat classes
-keep class com.moat.** {*;}
-dontwarn com.moat.**
# skip IAB classes
-keep class com.iab.** {*;}
-dontwarn com.iab.**

#pollfish
-dontwarn com.pollfish.**
-keep class com.pollfish.** { *; }

#appbrain
-keep public class com.appbrain.KeepClass
-keep public class * implements com.appbrain.KeepClass
-keepclassmembers class * implements com.appbrain.KeepClass {
    <methods>;
}
-keep class android.webkit.JavascriptInterface
-dontwarn android.webkit.JavascriptInterface
-dontwarn com.appbrain.**

#kidoz
-keepclasseswithmembers class com.kidoz.** {*;}
-keep @interface org.greenrobot.eventbus.Subscribe
-keepclassmembers class * {
@org.greenrobot.eventbus.Subscribe <methods>;
}

#greedygames
-keep class androidx.palette.** {*;}
-keep class com.squareup.moshi.** {*;}

#revenuecat
-keep class com.revenuecat.purchases.** { *; }

##---------------End: proguard configuration for Gson ----------