<h3>Chatbot with Bert - Android Application</h3>
<h4><strong>Description</strong></h4>
<p>The <strong>Chatbot with Bert</strong> application is designed to be a standalone chatbot running directly on the device, eliminating the need for a server.</p>
<p>This chatbot leverages a <strong>reference text</strong> provided by the user to answer questions as accurately as possible.</p>
<p>For example, if you own a pizzeria, the reference text could include details like your operating hours, pizza flavors offered, delivery charges, and more.</p>
<p>When a user types questions such as:</p>
<ul>
<li><em>"What time do you open?"</em></li>
<li><em>"What types of pizza do you offer?"</em></li>
</ul>
<p>The chatbot processes the question and generates answers based on the provided reference text.</p>
<p>The application is powered by the <strong>BERT</strong> model to ensure responses are realistic and contextually appropriate.</p>
<p>&nbsp;</p>
<h4><strong>Technical Details</strong></h4>
<ul>
<li><strong>Development Environment</strong>: Android Studio <em>Ladybug</em> | Version <strong>2024.2.1</strong></li>
<li><strong>Programming Language</strong>: Kotlin</li>
<li><strong>Architecture</strong>: Fully offline, runs directly on the device.</li>
</ul>
<p>&nbsp;</p>
<h4><strong>Features</strong></h4>
<ul>
<li><strong>Offline functionality</strong>: Operates without internet connectivity.</li>
<li><strong>Custom reference text</strong>: Users can configure their chatbot by simply defining a reference text.</li>
<li><strong>Natural Language Understanding</strong>: Built on the TensorFlow Lite implementation of the BERT model for realistic and context-aware responses.</li>
</ul>
<p>&nbsp;</p>
<h4><strong>Setup Instructions</strong></h4>
<h5><strong>1. Prerequisites</strong></h5>
<p>Ensure the following are ready before building the project:</p>
<ul>
<li>Android Studio <strong>2024.2.1 Ladybug</strong> or higher.</li>
<li>A physical or virtual Android device with API level 21 or above.</li>
</ul>
<h5><strong>2. Clone the Repository</strong></h5>
<p>Clone the project to your local machine:</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<pre><code>

git clone https://github.com/calbertobarbosajr/Chatbot-with-Bert.git

</code></pre>
<p>&nbsp;</p>
<h5><strong>3. Open the Project</strong></h5>
<ul>
<li>Open the project folder in Android Studio.</li>
<li>Sync Gradle to ensure all dependencies are downloaded.</li>
</ul>
<h5><strong>4. Gradle Configurations</strong></h5>
<p>Ensure the following configurations in your <strong><code>app/build.gradle</code></strong> file:</p>
<p>&nbsp;</p>
<pre><code>

android {
namespace = "com.calberto_barbosa_jr.bert"
compileSdk = 35

    viewBinding { enable = true }
}

dependencies {
implementation("org.tensorflow:tensorflow-lite:2.12.0")
implementation("org.tensorflow:tensorflow-lite-support:0.3.1")
implementation("org.tensorflow:tensorflow-lite-metadata:0.3.0")
implementation("org.tensorflow:tensorflow-lite-task-text:0.4.4")
}

</code></pre>
<p>&nbsp;</p>
<h5><strong>5. Build and Run</strong></h5>
<ul>
<li>Connect your device or start an emulator.</li>
<li>Click <strong>Run</strong> to install and launch the application.</li>
</ul>
<p>&nbsp;</p>
<h4><strong>Usage Instructions</strong></h4>
<ol>
<li>Launch the app on your Android device.</li>
<li>Provide a <strong>reference text</strong> through the app's setup interface.
<ul>
<li>Example: <em>"Our store is open from 9 AM to 6 PM. We offer Margherita, Pepperoni, and Hawaiian pizzas. Delivery is available for $5."</em></li>
</ul>
</li>
<li>Ask questions in natural language, such as:
<ul>
<li><em>"What time does your store open?"</em></li>
<li><em>"What are the delivery charges?"</em></li>
</ul>
</li>
<li>The chatbot will respond based on the reference text.</li>
</ol>
<p>&nbsp;</p>
<p>&nbsp;</p>
<h4><strong>Contributions</strong></h4>
<p>Contributions are welcome!<br />If you encounter any issues or have suggestions for improvement, feel free to open an <strong>Issue</strong> or submit a <strong>Pull Request</strong>.</p>
<p>&nbsp;</p>
<h4><strong>License</strong></h4>
<p>This project is licensed under the <strong>MIT License</strong>. Refer to the <a href="https://www.mit.edu/~amini/LICENSE.md" target="_new" rel="noopener">LICENSE</a> file for details.</p>
<p>&nbsp;</p>
<p>&nbsp;</p>