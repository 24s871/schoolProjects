----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/03/2023 03:02:51 PM
-- Design Name: 
-- Module Name: IDecoder - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity IDecoder is
  Port (clk:in STD_LOGIC;
        Instruction:in STD_LOGIC_VECTOR(12 downto 0);
        en:in STD_LOGIC;
        WD:in STD_LOGIC_VECTOR(15 downto 0);
        RegWrite:in STD_LOGIC;
        RegDst:in STD_LOGIC;
        ExtOp:in STD_LOGIC;
        RD1:out STD_LOGIC_VECTOR(15 downto 0);
        RD2:out STD_LOGIC_VECTOR(15 downto 0);
        Ext_Imm:out STD_LOGIC_VECTOR(15 downto 0);
        func:out STD_LOGIC_VECTOR(2 downto 0);
        sa:out STD_LOGIC);
end IDecoder;

architecture Behavioral of IDecoder is
component REGFILE is
   Port ( 
   RA1:in STD_LOGIC_VECTOR(2 downto 0);
   RA2:in STD_LOGIC_VECTOR(2 downto 0);
   clk:in STD_LOGIC;
   WA:in STD_LOGIC_VECTOR(2 downto 0);
   WD:in STD_LOGIC_VECTOR(15 downto 0);
   RD1:out STD_LOGIC_VECTOR(15 downto 0);
   RD2:out STD_LOGIC_VECTOR(15 downto 0);
   WEN:in STD_LOGIC); 
end component;
signal write_addres:STD_LOGIC_VECTOR(2 downto 0);
signal EXT:STD_LOGIC_VECTOR(15 downto 7);
begin
mux:process(Instruction(9 downto 7),Instruction(6 downto 4),RegDst)
begin
if(RegDst='0') then
write_addres<=Instruction(9 downto 7);
else write_addres<=Instruction(6 downto 4);
end if;
end process;
REG:REGFILE port map(RA1=>Instruction(12 downto 10),RA2=>Instruction(9 downto 7),WA=>write_addres,clk=>clk,WD=>WD,RD1=>RD1,RD2=>RD2,WEN=>RegWrite);
func<=Instruction(2 downto 0);
sa<=Instruction(3);
extindere:process(ExtOp)
begin
if(ExtOp='0') then
EXT<=(others=>'0');
else EXT<=(others=>Instruction(6));
end if;
Ext_Imm<=EXT&Instruction(6 downto 0);
end process;
end Behavioral;
