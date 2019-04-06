#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D sprite;
uniform vec3 color;
uniform float transparency;// TODO : Transparency with blur
uniform bool solidFilled = false;

void main()
{
    vec4 textureColor = texture(sprite, fragTexCoords);
    if (solidFilled) {
        if (textureColor.a == 0) {
            textureColor.rgb = color;
        }
        textureColor.a == 1;
        fragColor = vec4(color.rgb, 1.0) * textureColor;
    } else {
        textureColor *= transparency;
        fragColor = vec4(color.rgb, 1.0) * textureColor;
    }
}
