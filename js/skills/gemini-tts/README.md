<p align="center">
  <a href="https://github.com/runapi-ai/gemini-tts">
    <h3 align="center">Gemini TTS API Skill for RunAPI</h3>
  </a>
</p>

<p align="center">
  Install this agent skill, configure speakers and dialogue, then generate multi-speaker speech through the RunAPI CLI.
</p>

<p align="center">
  <a href="https://runapi.ai/models/gemini-tts"><strong>Model Reference</strong></a> · <a href="https://github.com/runapi-ai/cli"><strong>CLI</strong></a> · <a href="https://github.com/runapi-ai/gemini-tts-sdk"><strong>SDK</strong></a>
</p>

<div align="center">

[![skills.sh](https://www.skills.sh/b/runapi-ai/gemini-tts)](https://www.skills.sh/runapi-ai/gemini-tts/gemini-tts)
[![ClawHub](https://img.shields.io/badge/ClawHub-runapi--gemini--tts-111827)](https://clawhub.ai/runapi-ai/runapi-gemini-tts)
[![License](https://img.shields.io/github/license/runapi-ai/gemini-tts)](https://github.com/runapi-ai/gemini-tts/blob/main/LICENSE)

</div>
<br/>

Use Gemini TTS multi-speaker speech generation from Claude Code, Codex, Gemini CLI, Cursor, and 50+ skill-compatible agents.

The canonical agent file is `skills/gemini-tts/SKILL.md`.

## Variants

- Gemini 2.5 Pro TTS: select `gemini-2.5-pro-tts` for Gemini 2.5 Pro speech generation.
- Gemini 3.1 Flash TTS: select `gemini-3.1-flash-tts` for Gemini 3.1 Flash speech generation.

Both variants use the same speaker and dialogue request structure.

## Install

```bash
npx skills add runapi-ai/gemini-tts -g
```

Or paste this prompt to your AI agent:

```text
Install the gemini-tts skill for me:

1. Clone https://github.com/runapi-ai/gemini-tts
2. Copy the skills/gemini-tts/ directory into your
   user-level skills directory.
3. Verify that SKILL.md is present.
4. Confirm the install path when done.
```

Manual locations:

- Claude Code: `~/.claude/skills/gemini-tts/SKILL.md`
- Codex: `~/.codex/skills/gemini-tts/SKILL.md`
- Gemini CLI: `~/.gemini/skills/gemini-tts/SKILL.md`

## Quick example

```shell
runapi gemini-tts text-to-speech --input-file request.json
```

Links: [model](https://runapi.ai/models/gemini-tts), [API reference](https://runapi.ai/docs/api/gemini-tts/text-to-speech), [SDK](https://github.com/runapi-ai/gemini-tts-sdk), [catalog](https://runapi.ai/models).

Licensed under the Apache License, Version 2.0.
